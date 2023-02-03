package com.ruoyi.maintenance.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * 永久二维码请求体
 * @author Abel
 * @since 2/3/2023 2:38 PM
 */
@Data
public class SceneBody<V> {
	@JsonProperty("action_name")
	private String actionName;
	@JsonProperty("action_info")
	private Map<String, Map<String, V>> actionInfo;

	@Override
	public String toString() {
		return "SceneBody{" +
				"actionName='" + actionName + '\'' +
				", actionInfo=" + actionInfo +
				'}';
	}
}
