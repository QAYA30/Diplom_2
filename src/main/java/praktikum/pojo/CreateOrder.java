package praktikum.pojo;

import java.util.List;
public class CreateOrder {
    public List<String> ingredients;

    @Override
    public String toString() {
        return String.format("praktikum.pojo.CreateOrder{ingredients='%s' }", ingredients);
    }
    public List<String> getIngredients() {
        return ingredients;
    }
    public CreateOrder(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
