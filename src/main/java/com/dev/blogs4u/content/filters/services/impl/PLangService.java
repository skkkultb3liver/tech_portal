package com.dev.blogs4u.content.filters.services.impl;

import com.dev.blogs4u.content.filters.dto.CreatePLangRequest;
import com.dev.blogs4u.content.filters.entities.PLang;
import com.dev.blogs4u.content.filters.exceptions.PLangException;
import com.dev.blogs4u.content.filters.repositories.PLangRepository;
import com.dev.blogs4u.content.filters.services.IPLangService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PLangService implements IPLangService {

    private final PLangRepository pLangRepository;

    @Override
    public PLang savePLang(CreatePLangRequest request) {
        return pLangRepository.save(
                PLang.builder()
                        .name(request.getName())
                        .slug(request.getName().toLowerCase().replace(' ', '_'))
                        .build()
        );
    }

    @Override
    public List<PLang> saveMultiplePLangs(List<CreatePLangRequest> requests) {

        List<PLang> pLangs = requests.stream().map(

                request -> PLang.builder()
                        .name(request.getName())
                        .slug(request.getName().toLowerCase().replace(' ', '_'))
                        .build()
        ).toList();

        return pLangRepository.saveAll(pLangs);

    }

    @Override
    public PLang findPLangBySlug(String pLangSlug) {
        return pLangRepository.findBySlug(pLangSlug).orElseThrow(
                () -> new PLangException("Не удалось найти указанный язык")
        );
    }

    @Override
    public PLang findPLangById(Long pLangId) {
        return pLangRepository.findById(pLangId).orElseThrow(
                () -> new PLangException("Не удалось найти указанный язык")
        );
    }

    @Override
    public List<PLang> findAllPLangs() {
        return pLangRepository.findAll();
    }
}
