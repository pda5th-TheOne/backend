package pda5th.backend.theOne.dto;

import pda5th.backend.theOne.entity.DailyBoard;
import pda5th.backend.theOne.entity.Practice;
import pda5th.backend.theOne.entity.TIL;
import pda5th.backend.theOne.entity.Question;

import java.util.List;

public record DailyBoardDetails(
        DailyBoard dailyBoard,
        List<Practice> top3Practices,
        List<TIL> top3TILs,
        List<Question> top3Questions
) {}
