/**
 * BladeX Commercial License Agreement
 * Copyright (c) 2018-2099, https://bladex.cn. All rights reserved.
 * <p>
 * Use of this software is governed by the Commercial License Agreement
 * obtained after purchasing a license from BladeX.
 * <p>
 * 1. This software is for development use only under a valid license
 * from BladeX.
 * <p>
 * 2. Redistribution of this software's source code to any third party
 * without a commercial license is strictly prohibited.
 * <p>
 * 3. Licensees may copyright their own code but cannot use segments
 * from this software for such purposes. Copyright of this software
 * remains with BladeX.
 * <p>
 * Using this software signifies agreement to this License, and the software
 * must not be used for illegal purposes.
 * <p>
 * THIS SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY. The author is
 * not liable for any claims arising from secondary or illegal development.
 * <p>
 * Author: DreamLu (596392912@qq.com)
 */
package org.springblade.core.auto.factories;

import org.springblade.core.auto.annotation.AutoIgnore;
import org.springblade.core.auto.common.AbstractBladeProcessor;
import org.springblade.core.auto.common.BootAutoType;
import org.springblade.core.auto.common.MultiSetMap;
import org.springblade.core.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * spring boot 自动配置处理器
 *
 * @author L.cm
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("*")
@SupportedOptions("debug")
public class AutoFactoriesProcessor extends AbstractBladeProcessor {
	/**
	 * 处理的注解 @FeignClient
	 */
	private static final String FEIGN_CLIENT_ANNOTATION = "org.springframework.cloud.openfeign.FeignClient";
	/**
	 * Feign 自动配置
	 */
	private static final String FEIGN_AUTO_CONFIGURE_KEY = "org.springblade.core.cloud.feign.BladeFeignAutoConfiguration";
	/**
	 * The location to look for factories.
	 * <p>Can be present in multiple JAR files.
	 */
	private static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";
	/**
	 * devtools，有 Configuration 注解的 jar 一般需要 devtools 配置文件
	 */
	private static final String DEVTOOLS_RESOURCE_LOCATION = "META-INF/spring-devtools.properties";
	/**
	 * AutoConfiguration 注解
	 */
	private static final String AUTO_CONFIGURATION = "org.springframework.boot.autoconfigure.AutoConfiguration";
	/**
	 * AutoConfiguration imports out put
	 */
	private static final String AUTO_CONFIGURATION_IMPORTS_LOCATION = "META-INF/spring/" + AUTO_CONFIGURATION + ".imports";
	/**
	 * 数据承载
	 */
	private final MultiSetMap<String, String> factories = new MultiSetMap<>();
	/**
	 * spring boot 2.7 @AutoConfiguration
	 */
	private final Set<String> autoConfigurationImportsSet = new LinkedHashSet<>();
	/**
	 * 元素辅助类
	 */
	private Elements elementUtils;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		elementUtils = processingEnv.getElementUtils();
	}

	@Override
	protected boolean processImpl(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		if (roundEnv.processingOver()) {
			// 1. 生成 spring boot 2.7.x @AutoConfiguration
			generateAutoConfigurationImportsFiles();
			// 2. 生成 spring.factories
			generateFactoriesFiles();
		} else {
			processAnnotations(annotations, roundEnv);
		}
		return false;
	}

	private void processAnnotations(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		// 日志 打印信息 gradle build --debug
		log(annotations.toString());
		Set<? extends Element> elementSet = roundEnv.getRootElements();
		log("All Element set: " + elementSet.toString());

		// 过滤 TypeElement
		Set<TypeElement> typeElementSet = elementSet.stream()
			.filter(this::isClassOrInterface)
			.filter(TypeElement.class::isInstance)
			.map(e -> (TypeElement) e)
			.collect(Collectors.toSet());
		// 如果为空直接跳出
		if (typeElementSet.isEmpty()) {
			log("Annotations elementSet is isEmpty");
			return;
		}

		for (TypeElement typeElement : typeElementSet) {
			if (isAnnotation(elementUtils, typeElement, AutoIgnore.class.getName())) {
				log("Found @AutoIgnore annotation，ignore Element: " + typeElement.toString());
			} else if (isAnnotation(elementUtils, typeElement, FEIGN_CLIENT_ANNOTATION)) {
				log("Found @FeignClient Element: " + typeElement.toString());

				ElementKind elementKind = typeElement.getKind();
				// Feign Client 只处理 接口
				if (ElementKind.INTERFACE != elementKind) {
					fatalError("@FeignClient Element " + typeElement + " 不是接口。");
					continue;
				}

				String factoryName = typeElement.getQualifiedName().toString();
				if (factories.containsVal(factoryName)) {
					continue;
				}

				log("读取到新配置 spring.factories factoryName：" + factoryName);
				factories.put(FEIGN_AUTO_CONFIGURE_KEY, factoryName);
			} else {
				// 1. 生成 2.7.x 的 spi
				if (isAnnotation(elementUtils, typeElement, BootAutoType.COMPONENT_ANNOTATION)) {
					String autoConfigurationBeanName = typeElement.getQualifiedName().toString();
					autoConfigurationImportsSet.add(autoConfigurationBeanName);
					log("读取到自动配置 @AutoConfiguration：" + autoConfigurationBeanName);
				}
				// 2. 老的 spring.factories
				for (BootAutoType autoType : BootAutoType.values()) {
					String annotation = autoType.getAnnotationName();
					if (isAnnotation(elementUtils, typeElement, annotation)) {
						log("Found @" + annotation + " Element: " + typeElement.toString());

						String factoryName = typeElement.getQualifiedName().toString();
						if (factories.containsVal(factoryName)) {
							continue;
						}

						log("读取到新配置 spring.factories factoryName：" + factoryName);
						factories.put(autoType.getConfigureKey(), factoryName);
					}
				}
			}
		}
	}

	private void generateFactoriesFiles() {
		if (factories.isEmpty()) {
			return;
		}
		Filer filer = processingEnv.getFiler();
		try {
			// spring.factories 配置
			MultiSetMap<String, String> allFactories = new MultiSetMap<>();
			// 1. 用户手动配置项目下的 spring.factories 文件
			try {
				FileObject existingFactoriesFile = filer.getResource(StandardLocation.SOURCE_OUTPUT, "", FACTORIES_RESOURCE_LOCATION);
				// 查找是否已经存在 spring.factories
				log("Looking for existing spring.factories file at " + existingFactoriesFile.toUri());
				MultiSetMap<String, String> existingFactories = FactoriesFiles.readFactoriesFile(existingFactoriesFile, elementUtils);
				log("Existing spring.factories entries: " + existingFactories);
				allFactories.putAll(existingFactories);
			} catch (IOException e) {
				log("spring.factories resource file not found.");
			}
			// 2. 增量编译，已经存在的 spring.factories 文件
			try {
				FileObject existingFactoriesFile = filer.getResource(StandardLocation.CLASS_OUTPUT, "", FACTORIES_RESOURCE_LOCATION);
				// 查找是否已经存在 spring.factories
				log("Looking for existing spring.factories file at " + existingFactoriesFile.toUri());
				MultiSetMap<String, String> existingFactories = FactoriesFiles.readFactoriesFile(existingFactoriesFile, elementUtils);
				log("Existing spring.factories entries: " + existingFactories);
				allFactories.putAll(existingFactories);
			} catch (IOException e) {
				log("spring.factories resource file did not already exist.");
			}
			// 3. 处理器扫描出来的新的配置
			allFactories.putAll(factories);
			log("New spring.factories file contents: " + allFactories);
			FileObject factoriesFile = filer.createResource(StandardLocation.CLASS_OUTPUT, "", FACTORIES_RESOURCE_LOCATION);
			try (OutputStream out = factoriesFile.openOutputStream()) {
				FactoriesFiles.writeFactoriesFile(allFactories, out);
			}
			// 4. devtools 配置，因为有 @Configuration 注解的需要 devtools
			String classesPath = factoriesFile.toUri().toString().split("classes")[0];
			Path projectPath = Paths.get(new URI(classesPath)).getParent();
			String projectName = projectPath.getFileName().toString();
			FileObject devToolsFile = filer.createResource(StandardLocation.CLASS_OUTPUT, "", DEVTOOLS_RESOURCE_LOCATION);
			try (OutputStream out = devToolsFile.openOutputStream()) {
				FactoriesFiles.writeDevToolsFile(projectName, out);
			}
		} catch (IOException | URISyntaxException e) {
			fatalError(e);
		}
	}

	private void generateAutoConfigurationImportsFiles() {
		if (autoConfigurationImportsSet.isEmpty()) {
			return;
		}
		Filer filer = processingEnv.getFiler();
		try {
			// AutoConfiguration 配置
			Set<String> allAutoConfigurationImports = new LinkedHashSet<>();
			// 1. 用户手动配置项目下的 AutoConfiguration 文件
			try {
				FileObject existingFactoriesFile = filer.getResource(StandardLocation.SOURCE_OUTPUT, "", AUTO_CONFIGURATION_IMPORTS_LOCATION);
				// 查找是否已经存在 spring.factories
				log("Looking for existing AutoConfiguration imports file at " + existingFactoriesFile.toUri());
				Set<String> existingSet = FactoriesFiles.readAutoConfigurationImports(existingFactoriesFile);
				log("Existing AutoConfiguration imports entries: " + existingSet);
				allAutoConfigurationImports.addAll(existingSet);
			} catch (IOException e) {
				log("AutoConfiguration imports resource file not found.");
			}
			// 2. 增量编译，已经存在的配置文件
			try {
				FileObject existingFactoriesFile = filer.getResource(StandardLocation.CLASS_OUTPUT, "", AUTO_CONFIGURATION_IMPORTS_LOCATION);
				// 查找是否已经存在 spring.factories
				log("Looking for existing AutoConfiguration imports file at " + existingFactoriesFile.toUri());
				Set<String> existingSet = FactoriesFiles.readAutoConfigurationImports(existingFactoriesFile);
				log("Existing AutoConfiguration imports entries: " + existingSet);
				allAutoConfigurationImports.addAll(existingSet);
			} catch (IOException e) {
				log("AutoConfiguration imports resource file did not already exist.");
			}
			// 3. 处理器扫描出来的新的配置
			allAutoConfigurationImports.addAll(autoConfigurationImportsSet);
			log("New AutoConfiguration imports file contents: " + allAutoConfigurationImports);
			FileObject autoConfigurationImportsFile = filer.createResource(StandardLocation.CLASS_OUTPUT, "", AUTO_CONFIGURATION_IMPORTS_LOCATION);
			try (OutputStream out = autoConfigurationImportsFile.openOutputStream()) {
				FactoriesFiles.writeAutoConfigurationImportsFile(allAutoConfigurationImports, out);
			}
		} catch (IOException e) {
			fatalError(e);
		}
	}

	private boolean isClassOrInterface(Element e) {
		ElementKind kind = e.getKind();
		return kind == ElementKind.CLASS || kind == ElementKind.INTERFACE;
	}

	private boolean isAnnotation(Elements elementUtils, Element e, String annotationFullName) {
		List<? extends AnnotationMirror> annotationList = elementUtils.getAllAnnotationMirrors(e);
		for (AnnotationMirror annotation : annotationList) {
			// 如果是对于的注解
			if (isAnnotation(annotationFullName, annotation)) {
				return true;
			}
			// 处理组合注解
			Element element = annotation.getAnnotationType().asElement();
			String elementStr = element.toString();
			// 如果是 java 元注解，继续循环
			if (elementStr.startsWith("java.lang") || elementStr.startsWith("kotlin.")) {
				continue;
			}
			// 递归处理 组合注解
			if (isAnnotation(elementUtils, element, annotationFullName)) {
				return true;
			}
		}
		return false;
	}

	private boolean isAnnotation(String annotationFullName, AnnotationMirror annotation) {
		return annotationFullName.equals(annotation.getAnnotationType().toString());
	}
}
