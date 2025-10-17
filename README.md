# simulador_asignaciones_uvg

# Version: Entrega 3
Proyecto universitario de POO de la UVG para simular el proceso de inscripción de materias en estudiantes de nuevo ingreso.

## Integrantes
- Héctor Duarte - 25939  
- Edgar Guevara - 251154  
- María Salvatierra - 251036  
- Christopher Serrano - 251375  

---

## Descripción del problema
Uno de los principales retos que enfrentamos los estudiantes de nuevo ingreso es el proceso de asignación de materias. Algunos se quedaron sin poder asignarse, otros con agujeros en los horarios, y otros con retrasos curriculares dado que no sabían bien el procesos correcto.
Las plataformas digitales suelen ser poco intuitivas, los horarios se llenan rápido y no siempre hay suficiente orientación.  

Este sistema busca simular el proceso de inscripción, permitiendo a los estudiantes:
- Visualizar las materias disponibles.
- Verificar la disponibilidad de cupos.
- Organizar su horario.
- Generar un PDF con las materias inscritas y así tener un plan para asignarse en el dia de 

---

## Clases principales
- **Estudiante** → Representa a los estudiantes y sus materias inscritas.  
- **Materia** → Contiene información de nombre, cupos y horario.  
- **Inscripcion** → Maneja el proceso de inscripción y notificaciones.  
- **Plataforma** → Simula la interfaz de inscripción.  
- **Horario** → Verifica conflictos de horario.  
- **Asesor** → Brinda orientación y sugerencias a los estudiantes.  

---

## Requisitos funcionales
- Simular el proceso de asignación real.  
- Verificar disponibilidad de cupos.  
- Mostrar cursos sugeridos según carrera.  
- Generar PDF con los horarios asignados.  

---

## Tecnologías utilizadas
- **Java** 
- **PostgreSQL** 
- **JDBC / Hibernate**
- **Swing**  
- **Pendiente de definir** 

## Para compilar usar:
javac -cp ".:libs/itextpdf-5.5.13.jar" PlataformaGUI.java

Y para ejecutarla:

java -cp ".:libs/itextpdf-5.5.13.jar" PlataformaGUI

## Usuarios predefinidos para testing:
| Usuario (código) | Contraseña | Carrera    |
| ---------------- | ---------- | ---------- |
| 25932          | 123        | Ingeniería |
| 251293          | 123        | Medicina   |
| 251190          | 123        | Ingeniería |
