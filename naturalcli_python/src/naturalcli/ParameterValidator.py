

"""
  This class checks parameters values against their types.
 
  @author Ferran Busquets
 
  @see IParameterType
"""
class ParameterValidator :

    parameterTypes=[]

    def __init__(self):
        """ 
          Creates a new instance of <code>ParameterValidator</code> with default parameter types
        """
        self.parameterTypes = DefaultParameterTypes.createSet()

    def __init__(self, parameterTypes) :
        """ 
          Creates a new instance of <code>ParameterValidator</code>
          
          @param parameterTypes the parameter types collection
        """
        self.parameterTypes = parameterTypes

    def validate(self, value, type) :
        """
          Validate a parameter value for a type
          
          @param value the parameter value
          @param type the parameter type name
          @return <code>None</code> if validated, otherwise a error message
          @throws UnknownParameterType raised if the parameter is not found
        """
        pt = getParameterType(type)
        # If not found throw Error
        if pt == None :
            raise UnknownParameterType, type
        # Validate the parameter
        return pt.validationMessage(value)

    def getParameterType(self, type) :
        """
          Gets the parameter type for the given type name
           
          @param type the type name
          @return the paramter type object
        """
        pt = None
        # Look for the parameter type
        for s in self.parameterTypes :
            if s.getParameterTypeName() == type :
                pt = s
                break
        return pt
