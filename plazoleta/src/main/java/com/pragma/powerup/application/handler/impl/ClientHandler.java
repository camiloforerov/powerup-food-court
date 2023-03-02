package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.response.RestaurantForClientResponseDto;
import com.pragma.powerup.application.handler.IClientHandler;
import com.pragma.powerup.application.mapper.IListRestaurantsClientMapper;
import com.pragma.powerup.domain.api.IClientServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientHandler implements IClientHandler {
    private final IClientServicePort clientServicePort;
    private final IListRestaurantsClientMapper listRestaurantsClientMapper;

    /**
     * Paginated list of restaurants
     *
     * @param page - page number to list
     * @param numberOfElements - elements to show per page
     * @return list paginated and alphabetically sorted
     * */
    @Override
    public List<RestaurantForClientResponseDto> listRestaurants(int page, int numberOfElements) {
        return clientServicePort.listRestaurants(page, numberOfElements).stream()
                .map(restaurant -> listRestaurantsClientMapper.toDto(restaurant))
                .collect(Collectors.toList());
    }
}
