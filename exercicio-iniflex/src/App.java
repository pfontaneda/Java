import java.util.*;
import java.lang.*;
import java.io.*;
import java.time.LocalDate;
import java.math.BigDecimal;

class Pessoa {
    public String nome;
    public LocalDate nascimento;
}

class Funcionario  extends Pessoa{
    public BigDecimal salario;
    public String func;

    public Funcionario(String nome, LocalDate nascimento, BigDecimal salario, String func) {
        this.nome = nome;
        this.nascimento = nascimento;
        this.salario = salario;
        this.func = func;
    }
    
    @Override
    public String toString() {
        return "Nome: " + nome + " Data de nascimento: " + nascimento + " Salario: " + salario + " funcao: " + func;
    }
}

public class Main {
    public static void main(String[] args) {
   
        Funcionario[] funcionarios;
        funcionarios = new Funcionario [10];
        funcionarios[0] = new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal(2009.44), "Operador");
        funcionarios[1] = new Funcionario("João", LocalDate.of(1990, 05, 12), new BigDecimal(2284.38), "Operador");
        funcionarios[2] = new Funcionario("Caio", LocalDate.of(1961, 05, 02), new BigDecimal(9836.14), "Coordenador");
        funcionarios[3] = new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal(19119.88), "Diretor");
        funcionarios[4] = new Funcionario("Alice", LocalDate.of(1995, 01, 05), new BigDecimal(2234.68), "Recepcionista");
        funcionarios[5] = new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal(1582.72), "Operador");
        funcionarios[6] = new Funcionario("Arthur", LocalDate.of(1993, 03, 31), new BigDecimal(4071.84), "Contador");
        funcionarios[7] = new Funcionario("Laura", LocalDate.of(1994, 07, 8), new BigDecimal(3017.45), "Gerente");
        funcionarios[8] = new Funcionario("Heloísa", LocalDate.of(2003, 05, 24), new BigDecimal(1606.85), "Eletricista");
        funcionarios[9] = new Funcionario("Helena", LocalDate.of(1996, 9, 02), new BigDecimal(2799.93), "Gerente");

        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        System.out.printf("%-15s%-20s%-15s%-15s\n", "|Nome ", "|Data Nascimento ", "|Salário ", "|Função ");

        funcionarios.forEach(funcionario -> {
            String nome = funcionario.getNome();
            String dataNascimento = funcionario.getDataNascimento().format(formatter);
            String salario = "R$ " + String.format("%,.2f", funcionario.getSalario());
            String funcao = funcionario.getFuncao();
            System.out.printf("%-15s%-20s%-15s%-15s\n", nome, dataNascimento, salario, funcao);
        });

        funcionarios.forEach(funcionario -> {
            BigDecimal aumento = funcionario.getSalario().multiply(new BigDecimal("0.10"));
            funcionario.setSalario(funcionario.getSalario().add(aumento));
        });

        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));
        
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.printf("%-15s", "| " + funcao);
            lista.forEach(f -> System.out.print("| " + f.getNome() + ", "));
            System.out.println();
        });

        funcionarios.stream()
                .filter(funcionario -> Arrays.stream(mesesAniversario)
                        .anyMatch(mes -> funcionario.getDataNascimento().getMonthValue() == mes))
                .forEach(funcionario -> System.out.println("Aniversariante: " + funcionario.getNome()));

        Funcionario maisVelho = Collections.max(funcionarios,
             Comparator.comparing(f -> f.getDataNascimento().until(LocalDate.now()).toTotalMonths()));
        System.out.println("Funcionário mais velho: " + maisVelho.getNome() +
                ", Idade: " + maisVelho.getDataNascimento().until(LocalDate.now()).toTotalMonths() / 12 + " anos");

        funcionarios.sort(Comparator.comparing(Funcionario::getNome));
        System.out.println("Funcionários por ordem alfabética:");
        funcionarios.forEach(f -> System.out.println("   " + f.getNome()));

        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        String totalSalariosFormatado = String.format("%,.2f", totalSalarios);
        System.out.println("Total dos salários: R$" + totalSalariosFormatado);
        
        System.out.println("3.12 Imprimir quantos salários mínimos ganha cada funcionário");
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(funcionario -> {
            BigDecimal salarioEmSalariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2,
                    RoundingMode.HALF_UP);
            System.out.println(funcionario.getNome() + " ganha " + salarioEmSalariosMinimos + " salários mínimos.");
        });

    }

}