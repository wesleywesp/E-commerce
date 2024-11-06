package com.wesploja.lojaweb.service;

import com.wesploja.lojaweb.controller.dto.adress.AddressSavedDTO;
import com.wesploja.lojaweb.controller.dto.user.DethalharDadosUserDTO;
import com.wesploja.lojaweb.infra.security.EncryptionService;
import com.wesploja.lojaweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private final EncryptionService encrypt;

    public AdminService(EncryptionService encrypt) {
        this.encrypt = encrypt;
    }


    public ResponseEntity<?> getUserById(Long id) {
        var user = userRepository.findById(id).orElse(null);
        var addressesDTO = user.getAddresses().stream().map(address -> {
            return new AddressSavedDTO(
                    encrypt.decrypt(address.getStreet()),
                    encrypt.decrypt(address.getCity()),
                    encrypt.decrypt(address.getState()),
                    encrypt.decrypt(address.getDistrict()),
                    encrypt.decrypt(address.getCodepostal()),
                    encrypt.decrypt(address.getNumber()),
                    encrypt.decrypt(address.getComplement())
            );

        }).collect(Collectors.toList());
        return ResponseEntity.ok(new DethalharDadosUserDTO(
                user,
                encrypt.decrypt(user.getEmail()),
                encrypt.decrypt(user.getPhone()),
                addressesDTO
        ));
    }

    public ResponseEntity<?> getUsers(Pageable pageable) {
        var users = userRepository.findAllByAtivoTrue(pageable);
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        var usersDTO = users.stream().map(user -> {
            var addressesDTO = user.getAddresses().stream().map(address -> {
                return new AddressSavedDTO(
                        encrypt.decrypt(address.getStreet()),
                        encrypt.decrypt(address.getCity()),
                        encrypt.decrypt(address.getState()),
                        encrypt.decrypt(address.getDistrict()),
                        encrypt.decrypt(address.getCodepostal()),
                        encrypt.decrypt(address.getNumber()),
                        encrypt.decrypt(address.getComplement())
                );

            }).collect(Collectors.toList());
            return new DethalharDadosUserDTO(
                    user,
                    encrypt.decrypt(user.getEmail()),
                    encrypt.decrypt(user.getPhone()),
                    addressesDTO
            );

        }).collect(Collectors.toList());

        return ResponseEntity.ok(usersDTO);
    }
}
