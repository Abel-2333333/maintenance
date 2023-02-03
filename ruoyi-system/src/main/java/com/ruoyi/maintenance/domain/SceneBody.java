package com.ruoyi.maintenance.domain;

import lombok.Data;

import java.util.Map;

/**
 * @author Abel
 * @since 2/3/2023 2:38 PM
 */
@Data
public class SceneBody {
	private String actionName;
	private Map<String, Map<String, ?>> actionInfo;
}
