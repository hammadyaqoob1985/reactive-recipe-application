package guru.springframework.recipe.project.recipeproject.services;

import guru.springframework.recipe.project.recipeproject.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.project.recipeproject.coverters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipe.project.recipeproject.domain.UnitOfMeasure;
import guru.springframework.recipe.project.recipeproject.repositories.UnitOfMeasureRepository;
import guru.springframework.recipe.project.recipeproject.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UomServiceImplTest {

    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    UomService uomService;

    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        uomService = new UomServiceImpl(unitOfMeasureToUnitOfMeasureCommand, unitOfMeasureReactiveRepository);
    }

    @Test
    public void getAllUoms() {

        Set<UnitOfMeasure> unitOfMeasureSet = new HashSet<>();

        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId("1");

        UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
        unitOfMeasure1.setId("2");

        unitOfMeasureSet.add(unitOfMeasure);
        unitOfMeasureSet.add(unitOfMeasure1);

        when(unitOfMeasureReactiveRepository.findAll()).thenReturn(Flux.just(unitOfMeasure,unitOfMeasure1));

        Flux<UnitOfMeasureCommand> unitOfMeasureCommands = uomService.getAllUoms();

        assertEquals(unitOfMeasureCommands.count().block(), Long.valueOf(2));
        verify(unitOfMeasureReactiveRepository, times(1)).findAll();

    }
}