package fr.ensicaen.ecole.genielogiciel.model.character;
public class ConcreteFactoryDUT extends AbstractFactoryStudent{
    private Student _student;
    public ConcreteFactoryDUT(){
        int randomInt = 1 + (int) (Math.random() * 6);
        if (randomInt == 1) {
            _student = createDilettante();
        } else if (randomInt == 2) {
            _student = createDiligent();
        } else {
            _student = createBrilliant();
        }
    }
    @Override
    public Dilettante createDilettante() {
        System.out.println("new Dilettante");
        return new Dilettante();
    }

    @Override
    public Diligent createDiligent() {
        System.out.println("new Diligent");
        return new Diligent();
    }

    @Override
    public Brilliant createBrilliant() {
        System.out.println("new Brilliant");
        return new Brilliant();
    }

    public Student getStudent() {
        return _student;
    }
}