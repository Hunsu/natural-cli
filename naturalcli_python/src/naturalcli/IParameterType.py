"""
  A parameter type for all the commands.
 
  @author Ferran Busquets
"""
class IParameterType :

	"""
	  Gets the parameter type name. 
	  
	  @return the name of the parameter type
	"""
	def getParameterTypeName() :
		pass
	
	"""
	  Checks if a parameter value is of this type 
	  of parameter.
	  
	  @param  the string to be validated as this parameter type
	  @return <code>true</code> if the validation it's right;
	          <code>false</code> otherwise
	"""
	def validateParameter(value) :
		pass

	"""
	  Checks if a parameter value is of this type 
	  of parameter and returns a detailed message
	  if the validation fails.
	  
	  @param  the string to be validated as this parameter type
	  @return <code>null</code> if the validation it's right;
	          a detailed message if something it's wrong
	"""
	def validationMessage(value):
		pass

	"""
	  Converts the string representing the parameter value to
	  the corresponding type value.
	   
	  @param strRepresentation the string representation of the value
	  @return real object value
	"""
	def convertParameterValue(strRepresentation) :
		pass		


