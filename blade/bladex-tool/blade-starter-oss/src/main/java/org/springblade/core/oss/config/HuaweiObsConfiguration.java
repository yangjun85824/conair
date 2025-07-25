package org.springblade.core.oss.config;

import com.aliyun.oss.OSSClient;
import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import lombok.AllArgsConstructor;
import org.springblade.core.oss.HuaweiObsTemplate;
import org.springblade.core.oss.props.OssProperties;
import org.springblade.core.oss.rule.BladeOssRule;
import org.springblade.core.oss.rule.OssRule;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author Tonny
 */
@AllArgsConstructor
@AutoConfiguration(after = OssConfiguration.class)
@EnableConfigurationProperties(OssProperties.class)
@ConditionalOnClass({OSSClient.class})
@ConditionalOnProperty(value = "oss.name", havingValue = "huaweiobs")
public class HuaweiObsConfiguration {
	private final OssProperties ossProperties;

	@Bean
	@ConditionalOnMissingBean(OssRule.class)
	public OssRule ossRule() {
		return new BladeOssRule(ossProperties.getTenantMode());
	}

	@Bean
	@ConditionalOnMissingBean(ObsClient.class)
	public ObsClient ossClient() {
		// 使用可定制各参数的配置类（ObsConfiguration）创建OBS客户端（ObsClient），创建完成后不支持再次修改参数
		ObsConfiguration conf = new ObsConfiguration ();

		conf.setEndPoint(ossProperties.getEndpoint());
		// 设置OSSClient允许打开的最大HTTP连接数，默认为1024个。
		conf.setMaxConnections(1024);
		// 设置Socket层传输数据的超时时间，默认为50000毫秒。
		conf.setSocketTimeout(50000);
		// 设置建立连接的超时时间，默认为50000毫秒。
		conf.setConnectionTimeout(50000);
		// 设置从连接池中获取连接的超时时间（单位：毫秒），默认不超时。
		conf.setConnectionRequestTimeout(1000);
		// 设置连接空闲超时时间。超时则关闭连接，默认为60000毫秒。
		conf.setIdleConnectionTime(60000);
		// 设置失败请求重试次数，默认为3次。
		conf.setMaxErrorRetry(5);

		return new ObsClient(ossProperties.getAccessKey(), ossProperties.getSecretKey(), conf);
	}

	@Bean
	@ConditionalOnMissingBean(HuaweiObsTemplate.class)
	@ConditionalOnBean({ObsClient.class, OssRule.class})
	public HuaweiObsTemplate huaweiobsTemplate(ObsClient obsClient, OssRule ossRule) {
		return new HuaweiObsTemplate(obsClient, ossProperties, ossRule);
	}
}
