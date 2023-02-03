package com.ruoyi.maintenance.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

/**
 * 临时二维码请求体
 *
 * @author Abel
 * @since 2/3/2023 9:40 PM
 */

public class EphemeralSceneBody<V> extends SceneBody<V> {
    @JsonProperty("expire_seconds")
    private long expireSeconds;

    public long getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(long expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EphemeralSceneBody<?> that = (EphemeralSceneBody<?>) o;
        return getExpireSeconds() == that.getExpireSeconds();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getExpireSeconds());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("actionName", getActionName())
                .append("actionInfo", getActionInfo().toString())
                .append("expireSeconds", String.valueOf(expireSeconds))
                .toString();
    }
}
