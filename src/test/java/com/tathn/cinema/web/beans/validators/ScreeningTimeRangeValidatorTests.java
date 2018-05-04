package com.tathn.cinema.web.beans.validators;

import com.tathn.cinema.domain.room.Room;
import com.tathn.cinema.domain.screening.Screening;
import com.tathn.cinema.domain.screening.ScreeningService;
import org.junit.Test;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScreeningTimeRangeValidatorTests {

    private final FacesContext facesContext = mock(FacesContext.class);
    private final UIComponent uiComponent = mock(UIComponent.class);
    private final ScreeningTimeRangeValidator screeningTimeRangeValidator;
    private final ScreeningService screeningService = mock(ScreeningService.class);

    public ScreeningTimeRangeValidatorTests() {
        screeningTimeRangeValidator = new ScreeningTimeRangeValidator(screeningService);
    }

    private void validateScreening(Screening testedScreening, Collection<Screening> screeningsToTestAganist) {
        long testId = 0;
        testedScreening.setId(testId++);
        for (Screening scr : screeningsToTestAganist) {
            scr.setId(testId++);
        }
        HashMap<String, Object> attributesMap = new HashMap<>();
        attributesMap.put("screening", testedScreening);
        when(uiComponent.getAttributes()).thenReturn(attributesMap);
        when(screeningService.findByDateAndRoom(any(Date.class), any(Room.class))).thenReturn(screeningsToTestAganist);
        screeningTimeRangeValidator.validate(facesContext, uiComponent, new Object());
    }

    private Date fromLocalDateTime(LocalDateTime localDateTime) {
        return Date.from(localDateTime
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    private Screening getScreeningWithSettedTimeRange(LocalDateTime startsAt, LocalDateTime finishesAt) {
        Screening screening = new Screening();
        screening.setStartsAt(fromLocalDateTime(startsAt));
        screening.setFinishesAt(fromLocalDateTime(finishesAt));
        return screening;
    }

    @Test(expected = ValidatorException.class)
    public void testValidateScreeningsColliding() {
        Screening testedScreening = getScreeningWithSettedTimeRange(LocalDateTime.now().plusMinutes(5), LocalDateTime.now().plusHours(1) );
        Screening collidingScreening = getScreeningWithSettedTimeRange(LocalDateTime.now().plusMinutes(15), LocalDateTime.now().plusHours(2));
        List<Screening> screeningsToCompareWith = new ArrayList<>();
        screeningsToCompareWith.add(collidingScreening);
        validateScreening(testedScreening, screeningsToCompareWith);
    }

    @Test
    public void testValidateScreeningsNotColliding() {
        Screening testedScreening = getScreeningWithSettedTimeRange(LocalDateTime.now().plusMinutes(5), LocalDateTime.now().plusHours(1) );
        Screening collidingScreening = getScreeningWithSettedTimeRange(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(4));
        List<Screening> screeningsToCompareWith = new ArrayList<>();
        screeningsToCompareWith.add(collidingScreening);
        validateScreening(testedScreening, screeningsToCompareWith);
    }
}