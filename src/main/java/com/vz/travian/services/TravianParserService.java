package com.vz.travian.services;


import com.google.gson.Gson;
import com.vz.travian.model.MapDetailsResponse;
import com.vz.travian.model.MapResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TravianParserService {

    private static final String USER_COOKIES_STRING = "t5mu=ZhmZws0VNlEdF9WS; gl5SessionKey=%7B%22key%22%3A%229960c7f28716b12eae5e%22%2C%22id%22%3A%22310537%22%7D; gl5PlayerId=310537; t5SessionKey=%7B%22key%22%3A%22fbb467a6a167425e5967%22%2C%22id%22%3A%221005%22%7D; _ga=GA1.2.1720164078.1484669926; t5socket=%22client588cee70f7ca2%22; village=537542670; msid=sn8qc8nn7h66ubg0dp5iag1vr5";
    private static final String USER_SESSION = "fbb467a6a167425e5967";
    private Logger logger = LoggerFactory.getLogger(TravianParserService.class);

    private Gson gson = new Gson();

    private RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    public void parse() throws URISyntaxException {
        List<MapResponse.Response.Map.Cell> cmon = findWithUnits(getOasises());
        logger.info(cmon.toString());
    }

    private List<MapResponse.Response.Map.Cell> findWithUnits(List<MapResponse.Response.Map.Cell> oasisList) {
        return oasisList.stream().filter(this::checkUnits).collect(Collectors.toList());
    }

    private boolean checkUnits(MapResponse.Response.Map.Cell c) {
        logger.info("Checking {}", c.getId());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Cookie",
                USER_COOKIES_STRING);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String body = String.format(
                "{\"controller\":\"cache\",\"action\":\"get\",\"params\":{\"names\":[\"MapDetails:%1$s\"]},\"session\":\"" + USER_SESSION + "\"}",
                c.getId());
        try {
            RequestEntity<String> requestEntity = new RequestEntity<>(body, httpHeaders, HttpMethod.POST,
                    new URI("http://ru5.kingdoms.com/api/?c=cache&a=get"));
            ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
            MapDetailsResponse mapDetailsResponse = gson.fromJson(responseEntity.getBody(),
                    MapDetailsResponse.class);
            MapDetailsResponse.MapDetails.Data.Troops troops = mapDetailsResponse.getCache().get(0).getData().getTroops();
            if (troops.getTribeId().equals("4") && (troops.getUnits().containsKey(8)
                    || troops.getUnits().containsKey(9) || troops.getUnits().containsKey(10))) {
                c.setTroops(troops.getUnits());
                logger.info("Found! {}|{}: {}", c.getX(), c.getY(), c.getTroops().toString());
                return true;
            }
        } catch (URISyntaxException e) {
            logger.error("SHT!", e);
        }
        return false;
    }


    private List<MapResponse.Response.Map.Cell> getOasises() throws URISyntaxException {
        RequestEntity<String> requestEntity = new RequestEntity<>(HttpMethod.GET, new URI(
                "http://ru5.kingdoms.com/api/external.php?action=getMapData&privateApiKey=cbf96c0259137a1e7d9577f965318d30"));
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        MapResponse mapResponse = gson.fromJson(responseEntity.getBody(), MapResponse.class);
        return mapResponse.getResponse().getMap().getCells().stream().filter(c -> !c.getOasis().equals("0") && !(c.getKingdomId() instanceof Double))
                .collect(Collectors.toList());
    }

}
