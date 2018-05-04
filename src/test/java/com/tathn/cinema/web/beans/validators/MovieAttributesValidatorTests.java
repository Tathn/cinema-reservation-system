package com.tathn.cinema.web.beans.validators;

import com.tathn.cinema.domain.movie.Movie;
import com.tathn.cinema.web.beans.validators.MovieAttributesValidator;
import org.junit.Test;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieAttributesValidatorTests {

    private final FacesContext facesContext = mock(FacesContext.class);
    private final UIComponent uiComponent = mock(UIComponent.class);
    private final MovieAttributesValidator movieAttributesValidator = new MovieAttributesValidator();

    private void validateMovie(Movie movie) {
        HashMap<String, Object> attributesMap = new HashMap<>();
        attributesMap.put("movie", movie);
        when(uiComponent.getAttributes()).thenReturn(attributesMap);
        movieAttributesValidator.validate(facesContext, uiComponent, new Object());
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNoDimensionsSelected() {
        validateMovie(new Movie());
    }

    @Test
    public void testValidateOneDimensionSelected() {
        Movie movie = new Movie();
        movie.setTwoDimensional(true);
        validateMovie(movie);
    }

}
