package com.example.premierleaguenewsfilter.mock

import com.example.premierleaguenewsfilter.dashboard.watched.PlayerItem
import com.example.premierleaguenewsfilter.dashboard.watched.SoccerPosition

val mockedPlayers = listOf(
    PlayerItem("Santi", "Cazorla", "Arsenal", SoccerPosition.MID, false),
    PlayerItem("Eden", "Hazard", "Chelsea", SoccerPosition.MID, false),
    PlayerItem("Harry", "Kane", "Tottenham", SoccerPosition.FWD, true),
    PlayerItem("Tanguy", "Ndombele", "Tottenham", SoccerPosition.MID, false),
    PlayerItem("Virgil", "van Dijk", "Liverpool", SoccerPosition.DEF, false),
    PlayerItem("Mesut", "Ozil", "Arsenal", SoccerPosition.MID, true),
    PlayerItem("Kevin", "de Bruyne", "Manchester City", SoccerPosition.MID, false),
    PlayerItem("Callum", "Chambers", "Arsenal", SoccerPosition.DEF, false),
    PlayerItem("Marcus", "Rashford", "Manchester United", SoccerPosition.FWD, false),
    PlayerItem("Rui", "Patricio", "Wolverhampton", SoccerPosition.GK, true),
    PlayerItem("Chris", "Smalling", "Manchester United", SoccerPosition.DEF, false)
)