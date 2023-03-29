package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Chain;
import com.example.springbootproject.projection.ChainName;
import com.example.springbootproject.repository.ChainRepository;
import com.example.springbootproject.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/chains")
public class ChainController {

    private final ChainRepository repository;
    private final MemberRepository memberRepo;

    public ChainController(ChainRepository chainRepository, MemberRepository memberRepository) {
            repository = chainRepository;
            memberRepo = memberRepository;
        }


    @GetMapping("/{id}")
    ChainName getName(@PathVariable long id) {
        return repository.findNameById(id);
    }

    @GetMapping
    List<Chain> getChains() {
        return repository.findAll();
    }

    @GetMapping("/dto")
    List<ChainName> getAllDtoNames() {
        try {
            return repository.findAllNamesBy();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/dto/{id}")
    ChainName getOneDtoName(@PathVariable long id) {
        try {
            return repository.findNameById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{id}/members")
    Chain listMembersOfChain(@PathVariable long id) {
        return repository.findChainById(id);
    }

    @PostMapping
    ResponseEntity<Void> addChain(@RequestBody Chain chain) {
        System.out.println("HELLO");
        String name = chain.getName();
        if (name == null || name.isEmpty())
            throw new IllegalStateException();
        repository.save(chain);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(chain.getId()).toUri();
        return ResponseEntity.created(location).build();

        //response.setHeader("Location", location.toString());
        //return new ResponseEntity<>(chain, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable long id) {
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    ResponseEntity<Chain> updateChain(@PathVariable Long id, @RequestBody Chain chain) {
        Chain updateChain = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        updateChain.setName(chain.getName());
        updateChain.setAddress(chain.getAddress());
        updateChain.setMembers(chain.getMembers());

        repository.save(updateChain);

        return ResponseEntity.ok(updateChain);
    }

    @PutMapping("{chainId}/members/{memberId}")
    @Transactional
    public void addMemberToChain(@PathVariable Long memberId, @PathVariable Long chainId) {
        repository.findById(chainId)
                .ifPresent(chain -> chain.getMembers().add(memberRepo.findById(memberId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))));
    }


    @DeleteMapping("{chainId}/members/{memberId}")
    @Transactional
    public void deleteMemberFromChain(@PathVariable Long chainId, @PathVariable Long memberId) {
        repository.findById(chainId)
                .ifPresent(chain -> chain.getMembers().removeIf(member -> member.getId().equals(memberId)));
    }

}
