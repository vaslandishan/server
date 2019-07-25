package ir.server.vaslandishan.controller;

import ir.server.vaslandishan.models.Booth;
import ir.server.vaslandishan.payload.ApiResponse;
import ir.server.vaslandishan.payload.BoothRequest;
import ir.server.vaslandishan.payload.BoothResponse;
import ir.server.vaslandishan.payload.PagedResponse;
import ir.server.vaslandishan.repository.BoothRepository;
import ir.server.vaslandishan.repository.UserRepository;
import ir.server.vaslandishan.security.CurrentUser;
import ir.server.vaslandishan.security.UserPrincipal;
import ir.server.vaslandishan.service.BoothService;
import ir.server.vaslandishan.util.AppConstants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/booths")
public class BoothController {

    @Autowired
    private BoothRepository boothRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoothService boothService;

    private static final Logger logger = LoggerFactory.getLogger(BoothController.class);

    @GetMapping
    public PagedResponse<BoothResponse> getBooths(@CurrentUser UserPrincipal currentUser,
                                                @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return boothService.getAllBooths(currentUser, page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createBooth(@Valid @RequestBody BoothRequest boothRequest) {
        Booth booth = boothService.createBooth(boothRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{boothId}")
                .buildAndExpand(booth.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "booth Created Successfully"));
    }

    @GetMapping("/getall")
    public PagedResponse<BoothResponse> getBooth(@CurrentUser UserPrincipal currentUser,
                                                 @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                 @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return boothService.getAllBooths(currentUser, page, size);
    }

    @GetMapping("/{boothId}")
    public BoothResponse getBoothById(@CurrentUser UserPrincipal currentUser,
                                    @PathVariable Long boothId) {
        return boothService.getBoothById(boothId, currentUser);
    }

/*  @PostMapping("/{boothId}/votes") for product
    @PreAuthorize("hasRole('USER')")
    public BoothResponse castVote(@CurrentUser UserPrincipal currentUser,
                                 @PathVariable Long pollId,
                                 @Valid @RequestBody VoteRequest voteRequest) {
        return pollService.castVoteAndGetUpdatedPoll(boothId, voteRequest, currentUser);
    }*/


}
