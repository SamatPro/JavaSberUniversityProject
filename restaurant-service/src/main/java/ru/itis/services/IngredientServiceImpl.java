package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.models.Ingredient;
import ru.itis.repositories.IngredientRepository;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService{

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public Ingredient add(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Override
    public Ingredient get(Long id) {
        return ingredientRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Ingredient update(Long id, Ingredient ingredient) {
        Ingredient ingredientOld = ingredientRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        ingredientOld.setName(ingredient.getName());
        return ingredientRepository.save(ingredientOld);
    }

    @Override
    public void delete(Long id) {
        ingredientRepository.deleteById(id);
    }

    @Override
    public List<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }
}
