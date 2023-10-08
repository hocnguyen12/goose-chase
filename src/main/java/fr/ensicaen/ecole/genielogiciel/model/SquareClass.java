package fr.ensicaen.ecole.genielogiciel.model;

public class SquareClass implements Square{
    public SquareClass() {
    }

    @Override
    public void execute(AbstractFactoryStudent s) {
        s.increaseSkillLevel(1);
    }

    @Override
    public String get_squareNameFR() {
        return "Cours normal";
    }

    @Override
    public String get_squareNameEN() {
        return "Regular Class";
    }
}
