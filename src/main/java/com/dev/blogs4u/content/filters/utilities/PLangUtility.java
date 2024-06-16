package com.dev.blogs4u.content.filters.utilities;

import com.dev.blogs4u.content.filters.dto.CreatePLangRequest;
import com.dev.blogs4u.content.filters.services.IPLangService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PLangUtility {

    private final IPLangService pLangService;
    private final ResourceLoader resourceLoader;

    public void savePLangsFromSheetToDB() {

        Resource resource = resourceLoader.getResource("classpath:parsing/Languages.xlsx");
        List<CreatePLangRequest> requests = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(ResourceUtils.getFile(resource.getURL()))) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                String pLangName = row.getCell(0).getStringCellValue();
                String pLangSlug = row.getCell(1).getStringCellValue();

                requests.add(
                        CreatePLangRequest.builder()
                                .name(pLangName)
//                                .slug(pLangSlug)
                                .build()
                );
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        pLangService.saveMultiplePLangs(requests);
        log.info("[PLangUtils]: Success");
        return;
    }
}
