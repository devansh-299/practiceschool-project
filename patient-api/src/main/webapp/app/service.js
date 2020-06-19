angular.module('PatientApiApplication').factory('Patient', Patient);

Patient.$inject = ['$resource'];

function Patient($resource) {
	
	var resourceUrl = 'api/patients/:id';
	
	return $resource(resourceUrl, {}, {
		'update': {
			method: 'PUT'
		}
	});
}