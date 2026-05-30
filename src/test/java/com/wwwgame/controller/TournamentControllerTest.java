package com.wwwgame.controller;

import com.wwwgame.entity.Tournament;
import com.wwwgame.entity.TournamentStatus;
import com.wwwgame.service.PackService;
import com.wwwgame.service.TournamentService;
import com.wwwgame.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TournamentController.class)
class TournamentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TournamentService tournamentService;

    @MockBean
    private UserService userService;

    @MockBean
    private PackService packService;

    @Test
    @WithMockUser
    void detail_returnsTournamentDetailView_whenStatusWaiting() throws Exception {
        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setStatus(TournamentStatus.WAITING);

        when(tournamentService.findById(1L)).thenReturn(tournament);

        mockMvc.perform(get("/tournaments/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("tournament-detail"))
                .andExpect(model().attributeExists("tournament"));
    }

    @Test
    @WithMockUser
    void detail_redirectsToGame_whenStatusInProgress() throws Exception {
        Tournament tournament = new Tournament();
        tournament.setId(7L);
        tournament.setStatus(TournamentStatus.IN_PROGRESS);

        when(tournamentService.findById(7L)).thenReturn(tournament);

        mockMvc.perform(get("/tournaments/7"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/game/7"));
    }

    @Test
    @WithMockUser
    void detail_redirectsToGame_whenStatusFinished() throws Exception {
        Tournament tournament = new Tournament();
        tournament.setId(3L);
        tournament.setStatus(TournamentStatus.FINISHED);

        when(tournamentService.findById(3L)).thenReturn(tournament);

        mockMvc.perform(get("/tournaments/3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/game/3"));
    }
}
