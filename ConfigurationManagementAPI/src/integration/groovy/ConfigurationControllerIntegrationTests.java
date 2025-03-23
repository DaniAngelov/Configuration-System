import com.codingtask.configmanagementapi.repository.ConfigRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ConfigurationControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private ConfigRepository configRepository;
    private static final String URI = "http://localhost:8080/api/configs";

    @Test
    public void addConfiguration_whenCalled_shouldCreateNewConfiguration() throws Exception {
//        Configuration configuration = buildConfiguration("test", buildConfigurationVersion(),"dev");

//        mockMvc.perform(MockMvcRequestBuilders.get(URI)
//                .contentType(MediaType.APPLICATION_JSON))
    }

//    private Configuration buildConfiguration(String name, ConfigurationVersion configurationVersion, ""){
//        return Configuration.builder()
//                .
//                .build();
//    }

}
