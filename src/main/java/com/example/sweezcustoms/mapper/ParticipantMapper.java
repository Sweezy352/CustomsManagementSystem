package com.example.sweezcustoms.mapper;


import com.example.sweezcustoms.dto.view.ParticipantDto;
import com.example.sweezcustoms.entity.Participant;

public class ParticipantMapper {
    public static ParticipantDto toParticipantDto(Participant participant){
        return ParticipantDto.builder().id(participant.getId()).type(participant.getType()).status(participant.getStatus()).tin(participant.getTin()).build();
    }
}
