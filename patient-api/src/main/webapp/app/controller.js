angular.module("PatientApiApplication").controller("GeneralController", GeneralController);

GeneralController.inject = ['$scope', 'Patient'];

function GeneralController($scope, Patient) {
	$scope.patients = Patient.query();
	$scope.patient = {};
	$scope.buttonText = "Submit";
	
	$scope.savePatient = function() {
		
		if ($scope.patient.id !== undefined) {
			Patient.update($scope.patient, function() {
				$scope.patients = Patient.query();
				$scope.patient = {};
				$scope.buttonText = "Submit";
			});
		} else {
			Patient.save($scope.patient, function() {
				$scope.patients = Patient.query();
				$scope.patient = {};
			});
		}
	}
	
	$scope.updatePatientInit = function(patient) {
		$scope.buttonText = "Update";
		$scope.patient = patient;
	}
	
	$scope.deletePatient = function(patient) {
		patient.$delete({id: patient.id}, function() {
			$scope.patients = Patient.query();
		});
	}
}