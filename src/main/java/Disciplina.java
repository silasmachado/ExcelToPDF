
public class Disciplina {
	
	private String nome;
	
	private String data;
	
	private String mediaFinal;
	
	private String frequencia;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getMediaFinal() {
		return mediaFinal;
	}

	public void setMediaFinal(String mediaFinal) {
		this.mediaFinal = mediaFinal;
	}

	public String getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(String frequencia) {
		this.frequencia = frequencia;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Disciplina [nome=");
		builder.append(nome);
		builder.append(", data=");
		builder.append(data);
		builder.append(", mediaFinal=");
		builder.append(mediaFinal);
		builder.append(", frequencia=");
		builder.append(frequencia);
		builder.append("]");
		return builder.toString();
	}
}
