package zpt.dsaVis;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class selfping {
    private final RestTemplate rstTmp = new RestTemplate();

    @Scheduled(fixedRate = 50000)
    public void ping() {
        System.out.println("refreshed");
        rstTmp.getForObject("https://portfolio-gfky.onrender.com/",String.class);
    }
}
