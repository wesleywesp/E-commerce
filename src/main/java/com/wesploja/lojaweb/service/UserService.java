
package com.wesploja.lojaweb.service;

import com.wesploja.lojaweb.controller.dto.adress.CadastrarAddressDTO;
import com.wesploja.lojaweb.controller.dto.adress.AddressSavedDTO;
import com.wesploja.lojaweb.controller.dto.user.CadastrarUserDTO;
import com.wesploja.lojaweb.controller.dto.user.DadosUserDTO;
import com.wesploja.lojaweb.controller.dto.user.DethalharDadosUserDTO;
import com.wesploja.lojaweb.controller.dto.user.UpdatUserDTO;
import com.wesploja.lojaweb.doman.address.Address;
import com.wesploja.lojaweb.doman.user.User;

import com.wesploja.lojaweb.infra.security.EncryptionService;
import com.wesploja.lojaweb.repository.AddressRepository;
import com.wesploja.lojaweb.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private final EncryptionService encrypt;
    @Autowired
    private AddressRepository addressRepository;

    public UserService(EncryptionService encrypt) {
        this.encrypt = encrypt;
    }

    public ResponseEntity<?> cadastrarUser(CadastrarUserDTO dto, UriComponentsBuilder uriBuilder) {
        var user = new User(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(encrypt.encrypt(dto.email()));
        user.setPhone(encrypt.encrypt(dto.phone()));
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        userRepository.save(user);

        return ResponseEntity.created(uri).body(new DadosUserDTO(user, encrypt.decrypt(user.getEmail()),
                encrypt.decrypt(user.getPhone())));

    }

    public ResponseEntity<?> addAdress(@Valid CadastrarAddressDTO dto, UriComponentsBuilder uriBuilder, UserDetails userDetails) {
        var address = new Address(dto);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(address.getId()).toUri();
        var user = userRepository.findByUsername(userDetails.getUsername()).get();
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        address.setUser(user);//associar o endereço ao usuário
        address.setCodepostal(encrypt.encrypt(dto.codepostal()));
        address.setCity(encrypt.encrypt(dto.city()));
        address.setComplement(encrypt.encrypt(dto.complement()));
        address.setDistrict(encrypt.encrypt(dto.district()));
        address.setState(encrypt.encrypt(dto.state()));
        address.setStreet(encrypt.encrypt(dto.street()));
        address.setNumber(encrypt.encrypt(dto.number()));

        var addressSaved = addressRepository.save(address);

        return ResponseEntity.created(uri).body(new AddressSavedDTO(encrypt.decrypt(addressSaved.getStreet()),
                encrypt.decrypt(addressSaved.getCity()), encrypt.decrypt(addressSaved.getState()),
                encrypt.decrypt(addressSaved.getDistrict()),
                encrypt.decrypt(addressSaved.getCodepostal()), encrypt.decrypt(addressSaved.getNumber()),
                encrypt.decrypt(addressSaved.getComplement())));


    }
    public ResponseEntity<?> updateUser(@Valid UpdatUserDTO dto, UserDetails userDetails) {
        Optional<User> userOptional = userRepository.findByUsername(userDetails.getUsername());
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userOptional.get().atualizar(dto, passwordEncoder, encrypt);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deleteUser(UserDetails userDetails) {
        Optional<User> userOptional = userRepository.findByUsername(userDetails.getUsername());
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userOptional.get().setAtivo(false);
        return ResponseEntity.ok().build();

    }
    public ResponseEntity<?> getUserByAutho(UserDetails userDetails) {
        var user = userRepository.findByUsername(userDetails.getUsername()).get();
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
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
}

