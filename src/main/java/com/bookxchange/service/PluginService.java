package com.bookxchange.service;

import com.bookxchange.enums.EmailTemplateType;
import lombok.RequiredArgsConstructor;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.plugin.core.config.EnablePluginRegistries;

@EnablePluginRegistries(NotificationProcessingPlugin.class)
@RequiredArgsConstructor
public class PluginService {

    private final PluginRegistry<NotificationProcessingPlugin, EmailTemplateType> pluginRegistry;

    public NotificationProcessingPlugin getPlugin(EmailTemplateType emailTemplateType) {
        return pluginRegistry.getPluginFor(emailTemplateType);
    }
}
