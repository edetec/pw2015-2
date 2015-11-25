package br.senai.sc.ti2014n1.pw.bi.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

public class UploadImagemUtil {

	private static final Map<String, String> TIPOS_PERMITIDOS = new HashMap<String, String>();

	static {
		TIPOS_PERMITIDOS.put("image/jpeg", ".jpg");
		TIPOS_PERMITIDOS.put("image/pjpeg", ".jpg");
		TIPOS_PERMITIDOS.put("image/png", ".png");
		TIPOS_PERMITIDOS.put("image/gif", ".gif");
	}

	public static String copiar(Part foto, String fotoAntiga)
			throws IOException {

		if (foto == null) {
			return fotoAntiga;
		}

		String nomeFoto = gerarNomeFoto(foto);

		String caminhoAbsoluto = getCaminhoAbsoluto(nomeFoto);

		foto.write(caminhoAbsoluto);

		remover(fotoAntiga);

		return nomeFoto;
	}

	public static void remover(String fotoAntiga) {
		if (fotoAntiga == null) {
			return;
		}
		
		File arquivo = new File(getCaminhoAbsoluto(fotoAntiga));
		if (arquivo.exists()) {
			arquivo.delete();
		}
	}

	private static String getCaminhoAbsoluto(String nomeFoto) {
		String diretorioFotos = FacesContext.getCurrentInstance()
				.getExternalContext().getInitParameter("upload_path");
		String caminhoRelativo = "/resources/" + diretorioFotos + nomeFoto;

		ServletContext servletContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();

		String caminhoAbsoluto = servletContext.getRealPath(caminhoRelativo);

		return caminhoAbsoluto;
	}

	private static String gerarNomeFoto(Part foto) {
		if (!TIPOS_PERMITIDOS.containsKey(foto.getContentType())) {
			return null;
		}
		String extensao = TIPOS_PERMITIDOS.get(foto.getContentType());
		String nome = UUID.randomUUID().toString();
		return nome.concat(extensao);
	}

}
