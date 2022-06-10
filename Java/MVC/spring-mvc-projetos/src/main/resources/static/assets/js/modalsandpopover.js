/**
 * Script para Modal e PopOver Bootstrap 5.0.2
 */

/**
 * Modal Excluir
 */
let modalExcluir = document.getElementById('modalExcluir')
modalExcluir.addEventListener('show.bs.modal', function (event) {
	let button = event.relatedTarget;
	let modalObject = button.getAttribute('data-modal-object');
	let modalName = button.getAttribute('data-modal-name');
	let modalLink = button.getAttribute('data-modal-link');

	let modalTitle = modalExcluir.querySelector('.modal-title')
	let modalBody = modalExcluir.querySelector('.modal-body')
	let modalActionButton = modalExcluir.querySelector('.btn-secondary')

	modalTitle.textContent = 'Excluir ' + modalObject + ' ' + modalName
	modalBody.textContent = 'Deseja realmente excluir ' + modalObject + ' ' + modalName + '?';
	modalActionButton.href = modalLink;	 
})

/**
 * Modal Remover Desenvolvedor do Projeto
 */
let modalRemoverDesenvolvedorDoProjeto = document.getElementById('modalRemoverDesenvolvedorDoProjeto')
modalRemoverDesenvolvedorDoProjeto.addEventListener('show.bs.modal', function (event) {
	let button = event.relatedTarget;
	let modalDesenvolvedor = button.getAttribute('data-modal-desenvolvedor');
	let modalProjeto = button.getAttribute('data-modal-projeto');
	let modalLink = button.getAttribute('data-modal-link');

	let modalTitle = modalRemoverDesenvolvedorDoProjeto.querySelector('.modal-title')
	let modalBody = modalRemoverDesenvolvedorDoProjeto.querySelector('.modal-body')
	let modalActionButton = modalRemoverDesenvolvedorDoProjeto.querySelector('.btn-secondary')

	modalTitle.textContent = 'Remover desenvolvedor ' + modalDesenvolvedor + ' do projeto ' + modalProjeto
	modalBody.textContent = 'Deseja realmente remover o desenvolvedor ' + modalDesenvolvedor + ' do projeto ' + modalProjeto + '?';
	modalActionButton.href = modalLink;	 
	})

/**
 * PopOver
 */
let popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'))
let popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
	return new bootstrap.Popover(popoverTriggerEl)
})