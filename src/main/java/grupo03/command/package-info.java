/**
 * Patrón Command: operaciones de matrícula con deshacer, rehacer y bitácora.
 *
 * <ul>
 *   <li>Command: {@link grupo03.command.MatriculaCommand}</li>
 *   <li>ConcreteCommand: {@link grupo03.command.InscribirAsignaturaCommand},
 *       {@link grupo03.command.RetirarAsignaturaCommand},
 *       {@link grupo03.command.CambiarParaleloCommand}</li>
 *   <li>Receiver: {@link grupo03.command.Matricula}</li>
 *   <li>Invoker: {@link grupo03.command.MatriculaInvoker}</li>
 *   <li>Client: {@link grupo03.command.Principal}</li>
 * </ul>
 */
package grupo03.command;
