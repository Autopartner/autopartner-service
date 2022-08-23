package com.autopartner.service;

import com.autopartner.api.dto.request.ClientRequest;
import com.autopartner.api.dto.request.ClientRequestFixture;
import com.autopartner.domain.Client;
import com.autopartner.domain.ClientFixture;
import com.autopartner.repository.ClientRepository;
import com.autopartner.service.impl.ClientServiceImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PACKAGE)
class ClientServiceTest {
    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientServiceImpl clientService;

    @Captor
    ArgumentCaptor<Client> clientArgumentCaptor;

    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;

    List<Client> clients;

    Client client;
    ClientRequest request;

    Long id;

    @BeforeEach
    public void init() {
        client = ClientFixture.createClient();
        clients = List.of(client, new Client());
        request = ClientRequestFixture.createClientRequest();
        id = 3L;
    }

    @Test
    void findAll() {
        when(clientRepository.findByActiveTrue()).thenReturn(clients);
        List<Client> actualClients = clientService.findAll();
        assertThat(clients).isEqualTo(actualClients);
    }

    @Test
    void create() {
        clientService.create(request, id);
        verify(clientRepository).save(clientArgumentCaptor.capture());
        Client actualClient = clientArgumentCaptor.getValue();
        assertThatClientMappedCorrectly(actualClient);
    }

    @Test
    void findById() {
        when(clientRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.ofNullable(client));
        clientService.findById(client.getId());
        verify(clientRepository).findByIdAndActiveTrue(longArgumentCaptor.capture());
        id = longArgumentCaptor.getValue();
        assertThat(id).isEqualTo(client.getId());
    }

    @Test
    void update() {
        clientService.update(client, request);
        verify(clientRepository).save(clientArgumentCaptor.capture());
        Client actualClient = clientArgumentCaptor.getValue();
        assertThatClientMappedCorrectly(actualClient);
    }

    @Test
    void delete() {
        clientService.delete(client);
        verify(clientRepository).save(clientArgumentCaptor.capture());
        Client actualClient = clientArgumentCaptor.getValue();
        assertThat(actualClient).isEqualTo(client);
        assertThat(actualClient.getActive()).isFalse();
    }

    private void assertThatClientMappedCorrectly(Client actualClient) {
        assertThat(actualClient.getFirstName()).isEqualTo((request.getFirstName()));
        assertThat(actualClient.getLastName()).isEqualTo(request.getLastName());
        assertThat(actualClient.getCompanyName()).isEqualTo(request.getCompanyName());
        assertThat(actualClient.getAddress()).isEqualTo(request.getAddress());
        assertThat(actualClient.getPhone()).isEqualTo(request.getPhone());
        assertThat(actualClient.getEmail()).isEqualTo(request.getEmail());
        assertThat(actualClient.getProductDiscount()).isEqualTo(request.getProductDiscount());
        assertThat(actualClient.getTaskDiscount()).isEqualTo(request.getTaskDiscount());
        assertThat(actualClient.getClientType()).isEqualTo(request.getClientType());
        assertThat(actualClient.getNote()).isEqualTo(request.getNote());
    }
}
