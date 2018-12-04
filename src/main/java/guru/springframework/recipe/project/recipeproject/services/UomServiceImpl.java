package guru.springframework.recipe.project.recipeproject.services;

import guru.springframework.recipe.project.recipeproject.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.project.recipeproject.coverters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipe.project.recipeproject.domain.UnitOfMeasure;
import guru.springframework.recipe.project.recipeproject.repositories.UnitOfMeasureRepository;
import guru.springframework.recipe.project.recipeproject.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UomServiceImpl implements UomService{

    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    public UomServiceImpl(UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand, UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository) {
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
    }

//    @Override
//    public Set<UnitOfMeasureCommand> getAllUoms() {
//        return StreamSupport.stream(unitOfMeasureRepository.findAll()
//                .spliterator(), false)
//                .map(unitOfMeasureToUnitOfMeasureCommand::convert)
//                .collect(Collectors.toSet());
//    }


    @Override
    public Flux<UnitOfMeasureCommand> getAllUoms() {
        return unitOfMeasureReactiveRepository.findAll().map(unitOfMeasureToUnitOfMeasureCommand::convert);
    }
}
