import java.util.ArrayList;
import java.util.List;

public class Aluno {
	
	private String nome;
	
	private List<Disciplina> listaDisciplina = new ArrayList<Disciplina>();

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Disciplina> getListaDisciplina() {
		return listaDisciplina;
	}

	public void addDisciplina(Disciplina disciplina) {
		this.listaDisciplina.add(disciplina);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Aluno [nome=");
		builder.append(nome);
		builder.append(", listaDisciplina=");
		builder.append(listaDisciplina);
		builder.append("]");
		return builder.toString();
	}
}
