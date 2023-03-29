package com.example.springbootproject.controller;

import com.example.springbootproject.repository.ChainRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {

    private final ChainRepository chainRepository;

    public WebController(ChainRepository chainRepository) {
        this.chainRepository = chainRepository;
    }

    @GetMapping(value = "/allchains")
    String chains(Model model) {
        model.addAttribute("message", "Chains");
        model.addAttribute("listAllChains", chainRepository.findAll());
        return "chains";
    }

    @GetMapping(value = "allchains/{id}/members")
    String membersOfChain(Model model, @PathVariable long id) {
        model.addAttribute("chainName", chainRepository.findNameById(id).getName());
        model.addAttribute("message", "Members");
        model.addAttribute("membersOfChain", chainRepository.findChainById(id).getMembers());
        return "chainMembers";
    }

}
