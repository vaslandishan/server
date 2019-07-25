package ir.server.vaslandishan.controller;

import ir.server.vaslandishan.exception.ResourceNotFoundException;
import ir.server.vaslandishan.models.User;
import ir.server.vaslandishan.payload.*;
import ir.server.vaslandishan.repository.BoothRepository;
import ir.server.vaslandishan.repository.UserRepository;
import ir.server.vaslandishan.security.UserPrincipal;
import ir.server.vaslandishan.service.BoothService;
import ir.server.vaslandishan.security.CurrentUser;
import ir.server.vaslandishan.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoothRepository boothRepository;

/*    @Autowired
    private VoteRepository voteRepository;*/

    @Autowired
    private BoothService boothService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/me")
/*    @PreAuthorize("hasRole('USER')")*/
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {

        //UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
	long id= 1;
        UserSummary userSummary = new UserSummary(id, "Shahram4m", "shahram rostami");
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        long boothCount = 0;//boothRepository.countByCreatedBy(user.getId());
        long voteCount = 0;//voteRepository.countByUserId(user.getId());

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt(), boothCount, voteCount);

        return userProfile;
    }

/*    @GetMapping("/users/{username}/booths")
    public PagedResponse<BoothResponse> getBoothsCreatedBy(@PathVariable(value = "username") String username,
                                                         @CurrentUser UserPrincipal currentUser,
                                                         @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                         @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return boothService.getBoothsCreatedBy(username, currentUser, page, size);
    }


    @GetMapping("/users/{username}/votes")
    public PagedResponse<BoothResponse> getBoothsVotedBy(@PathVariable(value = "username") String username,
                                                       @CurrentUser UserPrincipal currentUser,
                                                       @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return boothService.getBoothsVotedBy(username, currentUser, page, size);
    }*/

}
