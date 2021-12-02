package com.example.rozetka_app.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "rozetka_static")
public class Statistics {
    @Id
    @Column
    private Long id;

    @Column(columnDefinition = "date")
    private LocalDate day;

    @Column()
    private String ips;

    public void addIpAddress(String ipAddress) {
        Map<String, Integer> map = this.getIpsAsMap();
        Integer count = map.getOrDefault(ipAddress, 0) + 1;
        map.put(ipAddress, count);
    }

    public Map<String, Integer> getIpsAsMap() {
        List<String[]> list = Arrays.stream(this.ips.split(";")).map(v -> v.split("=")).collect(Collectors.toList());
        Map<String, Integer> map = Collections.emptyNavigableMap();

        list.forEach(v -> {
            String key = v[0];
            Integer value = Integer.parseInt(v[1]);

            map.put(key, value);
        });

        return map;
    }

    private void setIpsAsString(Map<String, Integer> map) {
       Stream<String> stringStream = map.entrySet()
                                        .stream()
                                        .map(v -> String.format("%s=%d", v.getKey(), v.getValue()));
       this.ips = String.join(";", stringStream.collect(Collectors.toList()));
    }
}
