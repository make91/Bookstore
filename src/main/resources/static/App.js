class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
        books: [],
    };
   }

  componentDidMount() {
    this.loadBooksFromServer();
  }

  // Load students from database
  loadBooksFromServer() {
    fetch('http://localhost:8181/books', {
      credentials: 'same-origin'
    })
    .then((response) => response.json()) 
    .then((responseData) => { 
      console.log(responseData);
      this.setState({ 
          books: responseData, 
      }); 
    });
} 
  render() {
    return (
      <div>
        <h1>Booklist</h1>
        <BookTable books={this.state.books} />
      </div>
    );
  }
}

class BookTable extends React.Component {
  render() {
    return (
      <table className="table table-hover">
        <thead>
          <tr>
            <th>Title</th>
            <th>Author</th>
            <th>Year</th>
            <th>ISBN</th>
            <th>Price</th>
          </tr>
        </thead>
        <tbody>
          {this.props.books.map((book) => 
            <tr key={book.id}>
              <td>{book.title}</td>
              <td>{book.author}</td>
              <td>{book.year}</td>
              <td>{book.isbn}</td>
              <td>{book.price}</td>
            </tr>
          )}
        </tbody>
      </table>
    );
  }
}

ReactDOM.render(<App />, document.getElementById('root') );	