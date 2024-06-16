package com.dev.blogs4u.content.filters.services;

import com.dev.blogs4u.content.filters.dto.CreatePLangRequest;
import com.dev.blogs4u.content.filters.entities.PLang;

import java.util.List;

public interface IPLangService {

    PLang savePLang(CreatePLangRequest request);

    List<PLang> saveMultiplePLangs(List<CreatePLangRequest> requests);

    PLang findPLangBySlug(String pLangSlug);

    PLang findPLangById(Long pLangId);

    List<PLang> findAllPLangs();
}
