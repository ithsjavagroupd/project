package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Chain;
import com.example.springbootproject.entity.Store;
import com.example.springbootproject.projection.ChainName;
import com.example.springbootproject.repository.ChainRepository;
import com.example.springbootproject.repository.MemberRepository;
import com.example.springbootproject.repository.StoreRepository;
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
    private final StoreRepository storeRepository;

    public ChainController(ChainRepository chainRepository, MemberRepository memberRepository, StoreRepository storeRepository) {
        this.chainRepository = chainRepository;
        this.memberRepo = memberRepository;
        this.storeRepository = storeRepository;
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
        if (chainRepository.findById(id).isPresent())
            chainRepository.deleteById(id);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    ResponseEntity<Chain> updateChain(@PathVariable Long id, @RequestBody Chain chain) {
        Chain updateChain = chainRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        updateChain.setName(chain.getName());
        updateChain.setAddress(chain.getAddress());
        updateChain.setMembers(chain.getMembers());
        updateChain.setStores(chain.getStores());

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

    @PutMapping("{chainId}/stores/{storeId}")
    @Transactional
    public void addStoreToChain(@PathVariable Long chainId, @PathVariable Long storeId) {
            Store store = storeRepository.findById(storeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            Chain chain = chainRepository.findById(chainId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            chain.getStores().add(store);
}

    @DeleteMapping("{chainId}/stores/{storeId}")
    @Transactional
    public void deleteStoreFromChain(@PathVariable Long chainId, @PathVariable Long storeId) {
        chainRepository.findById(chainId)
                .ifPresent(chain -> chain.getStores().removeIf(store -> store.getId().equals(storeId)));
    }


    @DeleteMapping("{chainId}/stores/{storeId}")
    @Transactional
    public void deleteBranchFromChain(@PathVariable Long chainId, @PathVariable Long storeId) {
        chainRepository.findById(chainId)
                .ifPresent(chain -> chain.getStores().removeIf(store -> store.getId().equals(storeId)));
    }
}