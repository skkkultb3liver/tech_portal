package com.dev.blogs4u.content.guides.services;

import com.dev.blogs4u.content.guides.dto.ModuleCreationRequest;
import com.dev.blogs4u.content.guides.entities.Module;

public interface IModuleService {

    public Module createModule(ModuleCreationRequest request);

}
