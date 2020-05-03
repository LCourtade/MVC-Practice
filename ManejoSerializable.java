package Persistencia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ManejoSerializable {
	private String archivo;
	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;
	
	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public boolean crearArchivo() {
		boolean pude = true;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(archivo, false));
		} catch (FileNotFoundException e) {
			pude = !pude;
			e.printStackTrace();
		} catch (IOException e) {
			pude = !pude;
			e.printStackTrace();
		}
		return pude;
	};

	public void cerrarArchivo() {
		try {
			oos.close();
		} catch (Exception e) {
			// No hago nada
		}
		try {
			ois.close();
		} catch (Exception e) {
			// No hago nada
		}
	}

	public boolean guardarEnArchivo(Object o) {
		boolean pude = true;
		try {
			oos.writeObject(o);
		} catch (IOException e) {
			pude = !pude;
			e.printStackTrace();
		}
		return pude;
	}

	
	public boolean abrirParaLectura(){
		boolean pude = true;
		try {
			ois = new ObjectInputStream(new FileInputStream(archivo));
		} catch (IOException e) {
			pude = !pude;
			e.printStackTrace();
		}
		return pude;
	}
	
	public Object leerRegistro(){
		Object o = null;
		try {
			o = ois.readObject();
		} catch (ClassNotFoundException e) {
			
		} catch (IOException e) {
			
		}
		return o;
	}

	public boolean abrirParaAgregarArchivo() {
		boolean pude = true;
		try {
			oos = new MiObjectOutputStream(new FileOutputStream(archivo, true));
		} catch (FileNotFoundException e) {
			pude = !pude;
			e.printStackTrace();
		} catch (IOException e) {
			pude = !pude;
			e.printStackTrace();
		}
		return pude;
	};
	
}


