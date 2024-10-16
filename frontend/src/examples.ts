// Definition
// Primitives
const stringVar: string = 'this is a string';
const numericVar: number = 3;
const booleanVar: boolean = false;

// Special types provided in TS
const stringOrUndefined: string | undefined = undefined;
const stringOrNull: string | null = null;

function unknownFn(unknownProperty: unknown): boolean {
    return 'something' in unknownProperty;
}
// Bad practice
function anyFn(unknownProperty: any): boolean {
    return 'something' in unknownProperty;
}

type Occupy = 'student' | 'worker' | 'unemployed' | 'retired';

const myOcupation: Occupy = ''



const occupyList = ['student', 'worker', 'unemployed', 'retired'] as const





type OccupeList = typeof occupyList[number]



const dog = {
    age: 1,
    breed: 'golden' as const,
    timesInVet: 0,
    color: 'light yellow',
}
type Dog = typeof dog;


npm install -g create-react-app


// Array
const array: string[] = ['one', 'two'];
const array2: Array<string> = ['one', 'two'];

if (stringVar === numericVar) {
    // Do something
}

// function
function checkNull(something: unknown): boolean {
    return !!something;
}

function multiplyBy2(value: number): number {
    return value * 2
}
multiplyBy2(4)
multiplyBy2('4')

const itExists: boolean = checkNull(undefined);
if (itExists) {
    // Do something
}


// Type example
type Email = `${string}@${string}.${'com' | 'cat'}`;
type User = {
    name: string,
    email: Email,
    password: string
}
// Interface example
interface IUser {
    name: string,
    email: Email,
    password: string
}


const userFromType: User = {
    name: ' user',
    email: 'user@gmail.com',
    password: 'string'
}

const userFromInterface: IUser = {
    name: ' user',
    email: 'user@gmail.com',
    password: 'string'
}


// Inference
function existsUser(user: User) {
    return !!user;
}


const isUserThere = existsUser(userFromInterface);

const userNotTyped = {
    email: 'user@gmail.cat' as const,
    name: 'user',
    password: '12345'
};
const isUserWithInference = existsUser(userNotTyped);

const isUserWithInferenceError = existsUser({ name: 'jordi' });


function isUser(obj: any): obj is User {
    return 'name' in obj
        && 'password' in obj
        && 'email' in obj
}

const user = {};
if (isUser(user)) {
    console.log(user.email)
} else {
    console.log(user)
}



// Generics
type ResultsPagination<T> = {
    totalResults: number;
    currentInit: number;
    totalItemsInResults: number;
    value: T[]
}

const UserResutls: ResultsPagination<User> = {
    totalResults: 0,
    currentInit: 0,
    totalItemsInResults: 0,
    value: [userFromInterface, userFromType]
}

const StringResults: ResultsPagination<string> = {
    totalResults: 0,
    currentInit: 0,
    totalItemsInResults: 0,
    value: ['string 1', 'string 2']
}


type ExtendedType = Dog & { city: 'string', age: number };

const dogInfo: ExtendedType = {

}
type A = { name: string; age: number; }
type B = { name: string; salary: number; }

type AB = A | B;

const ab: AB = {
    name: ' s',
    salary: 9
}
const abd2: AB = {
    name: '',
    salary: 2
}


const userCast = { email: 'd@d.cat', name: '', password: '' } as User

const userCast2 = {
    email: '',
    name: '',
    password: ''
} as unknown as User


type Tuple = [string, number]

const tuple: Tuple = ['string', 0]


const tupleError: Tuple = [1, 0]


const UserDog: IUser & Dog = {

}



const UserDog: IUser | Dog = {

}


type Optional = { name?: string }
const optional: Optional = {}
type NonOptional = Required<Optional>;


type PickedDog = Pick<Dog, 'color' | 'age'>



type CheckNullReturn = ReturnType<typeof checkNull>


const nonOptional: NonOptional = {}



interface Todo {
    title: string;
  }
   
  const todo: Readonly<Todo> = {
    title: "Delete inactive users",
  };
   
  todo.title = "Hello";




console.log(userFromType,
    userFromInterface, nonOptional, optional
    isUserWithInference,
    isUserWithInferenceError,
    StringResults,
    UserResutls,
    isUserThere,
    stringVar,
    numericVar,
    booleanVar,
    array2,
    abd2, ab, tuple,
    array)

