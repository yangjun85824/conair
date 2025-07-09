package org.springblade.resource.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.resource.pojo.entity.Oss;

import java.io.Serial;

/**
 * OssVO
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "对象存储表")
public class OssVO extends Oss {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 分类名
	 */
	private String categoryName;

	/**
	 * 是否启用
	 */
	private String statusName;

}
