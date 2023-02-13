package com.ruoyi.web.controller.maintenance;

import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Abel
 * @since 2/13/2023 10:49 AM
 */
@RestController
@RequestMapping("/")
public class HealthCheckController {
	@GetMapping("/healthcheck")
	public AjaxResult healthCheck() {
		return AjaxResult.success("Health check is ok");
	}
}
