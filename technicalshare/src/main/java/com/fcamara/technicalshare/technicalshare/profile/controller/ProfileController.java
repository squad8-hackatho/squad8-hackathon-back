package com.fcamara.technicalshare.technicalshare.profile.controller;

import com.fcamara.technicalshare.technicalshare.profile.dto.ProfileDTO;
import com.fcamara.technicalshare.technicalshare.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    
    @GetMapping("/findall")
    public ResponseEntity<Page<ProfileDTO>> findAllProfile(@Nullable Pageable pageable) {
        return ResponseEntity.ok(profileService.findAllProfile(pageable));
    }

    @GetMapping("/findbyname")
    public ResponseEntity<Page<ProfileDTO>> findProfileByUserName (String name, @Nullable Pageable pageable) {
        return ResponseEntity.ok(profileService.findProfileByUserName(name, pageable));
    }
    
    @GetMapping("/findprofile")
    public ResponseEntity<ProfileDTO> findProfileByEmail(@RequestParam String email) {
        return ResponseEntity.ok(profileService.findProfile(email));
    }

    @PostMapping("register")
	public ResponseEntity<ProfileDTO> registerProfile(@RequestBody ProfileDTO profileDTO){
		return ResponseEntity.ok(profileService.registerProfile(profileDTO));
	}

    @GetMapping("/findbyskill")
    public ResponseEntity<Page<ProfileDTO>> findProfilesBySkill(@RequestParam String skill, @Nullable Pageable pageable) {
        return ResponseEntity.ok(profileService.getProfileBySkill(skill, pageable));
    }

}
