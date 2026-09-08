package fr.diginamic.hello.controleurs;


import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.repositories.DepartementRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Fail.fail;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class DepartementControleurTest {

    @MockitoBean
    private DepartementRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetDepartements() throws Exception {

        when(repository.findAll())
                .thenReturn(List.of(
                        new Departement(1, "01", "Ain"),
                        new Departement(2, "02", "Aisne")
                ));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/departements"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].code").value("01"))
                .andExpect(jsonPath("$[0].nom").value("Ain"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].code").value("02"))
                .andExpect(jsonPath("$[1].nom").value("Aisne"));
    }

}
