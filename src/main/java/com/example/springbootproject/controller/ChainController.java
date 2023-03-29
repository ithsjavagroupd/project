package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Chain;
import com.example.springbootproject.projection.ChainName;
import com.example.springbootproject.repository.ChainRepository;
import com.example.springbootproject.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/chains")
public class ChainController {

    private final ChainRepository chainRepository;
    private final MemberRepository memberRepo;

    public ChainController(ChainRepository chainRepository, MemberRepository memberRepository) {
        this.chainRepository = chainRepository;
        this.memberRepo = memberRepository;
    }

    @GetMapping("/{id}/name")
    ChainName getName(@PathVariable long id) {
        try {
            return chainRepository.findNameById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    Chain getById(@PathVariable long id) {
        return chainRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    List<Chain> getChains() {
        try {
            return chainRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/dto")
    List<ChainName> getAllDtoNames() {
        try {
            return chainRepository.findAllNamesBy();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/dto/{id}")
    ChainName getOneDtoName(@PathVariable long id) {
        try {
            return chainRepository.findNameById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/members")
    Chain listMembersOfChain(@PathVariable long id) {
        try {
            return chainRepository.findChainById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    ResponseEntity<Void> addChain(@RequestBody Chain chain) {
        String name = chain.getName();
        if (name.isEmpty())
            throw new IllegalStateException();

        chainRepository.save(chain);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(chain.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable long id) {
        chainRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    ResponseEntity<Chain> updateChain(@PathVariable Long id, @RequestBody Chain chain) {
        Chain updateChain = chainRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        updateChain.setName(chain.getName());
        updateChain.setAddress(chain.getAddress());
        updateChain.setMembers(chain.getMembers());

        chainRepository.save(updateChain);

        return ResponseEntity.ok(updateChain);
    }

    @PutMapping("{chainId}/members/{memberId}")
    @Transactional
    public void addMemberToChain(@PathVariable Long memberId, @PathVariable Long chainId) {
        chainRepository.findById(chainId)
                .ifPresent(chain -> chain.getMembers().add(memberRepo.findById(memberId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))));
    }

    @DeleteMapping("{chainId}/members/{memberId}")
    @Transactional
    public void deleteMemberFromChain(@PathVariable Long chainId, @PathVariable Long memberId) {
        chainRepository.findById(chainId)
                .ifPresent(chain -> chain.getMembers().removeIf(member -> member.getId().equals(memberId)));
    }


}
