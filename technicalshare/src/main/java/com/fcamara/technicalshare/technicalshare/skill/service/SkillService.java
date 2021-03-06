package com.fcamara.technicalshare.technicalshare.skill.service;

import com.fcamara.technicalshare.technicalshare.profile.model.Profile;
import com.fcamara.technicalshare.technicalshare.skill.dto.SkillDTO;
import com.fcamara.technicalshare.technicalshare.skill.model.Skill;
import com.fcamara.technicalshare.technicalshare.skill.repository.SkillRepository;

import com.fcamara.technicalshare.technicalshare.skill.repository.projection.ProfileProjection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    public Skill registerProfileSkill(SkillDTO skillDTO, Profile newProfile){
        Skill skillRegistered = skillRepository.findSkillBySkill(skillDTO.getSkill());
        skillRegistered.getProfileExpertiseList().add(newProfile);
        return skillRegistered;
    }

    public List<SkillDTO> registerSkill(List<SkillDTO> skillDTOList){
        skillDTOList.forEach (skillDTO -> {
            Skill newSkill = new Skill();
            newSkill.setSkill(skillDTO.getSkill());
            newSkill.setArea(skillDTO.getArea());
            skillRepository.save(newSkill);
        });
        return getAllSkills();
    }

    public List<SkillDTO> getAllSkills() {
        return skillRepository.findAll().stream().map(SkillDTO::convertToDTO).collect(Collectors.toList());
    }

    public Skill getSkillBySkill(String skill){
        return skillRepository.findSkillBySkill(skill);
    }

    public List<String> findByMultipleSkills(String firstSkill, String secondSkill) {
        List<ProfileProjection> projections = skillRepository.findByMultipleSkill(firstSkill, secondSkill);
        List<String> emailProfileList = new ArrayList<>();
        projections.forEach( profileProjection -> {
            emailProfileList.add(profileProjection.getEmail());
        });
        return emailProfileList;
    }

    @Transactional
    public void deleteSkillsBySkill(String skill) {
        skillRepository.deleteSkillBySkill(skill);
    }


    public SkillDTO updateSkill(String toChangeSkill, String newSkillName, String newAreaName) {
        Skill toUpdateSkill = getSkillBySkill(toChangeSkill);

        if(Objects.equals(newSkillName, null) && Objects.equals(newAreaName, null)) {
            throw new NullPointerException();
        } else if(Objects.equals(newSkillName, null)) {
            toUpdateSkill.setArea(newAreaName);
            return SkillDTO.convertToDTO(skillRepository.save(toUpdateSkill));
        } else if(Objects.equals(newAreaName, null)) {
            toUpdateSkill.setSkill(newSkillName);
            return SkillDTO.convertToDTO(skillRepository.save(toUpdateSkill));
        } else {
            toUpdateSkill.setArea(newAreaName);
            toUpdateSkill.setSkill(newSkillName);
            return SkillDTO.convertToDTO(skillRepository.save(toUpdateSkill));
        }
    }
}
