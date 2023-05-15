from flask import Flask, jsonify
from flask_restful import Api, Resource
from flask_mysqldb import MySQL

app = Flask(__name__)
api = Api(app)

app.config['MYSQL_HOST'] = 'localhost'
app.config['MYSQL_USER'] = 'root'
app.config['MYSQL_PASSWORD'] = ''
app.config['MYSQL_DB'] = 'bioskop'

db = MySQL(app)

class Movie(Resource):
    def get(self, movie_id):
        cursor = db.connection.cursor()

        if (movie_id) == 0:
            cursor.execute("SELECT id, title, poster_link FROM movie")
        else:
            cursor.execute("SELECT * FROM movie WHERE id=%s", (movie_id,))
        result = cursor.fetchall()
        result = [dict((cursor.description[i][0], value) \
               for i, value in enumerate(row)) for row in result]
        if movie_id == 0:
            return result
        else:
            return result[0]

class Theater(Resource):
    def get(self):
        cursor = db.connection.cursor()
        cursor.execute("SELECT * FROM theater")
        result = cursor.fetchall()
        result = [dict((cursor.description[i][0], value) \
               for i, value in enumerate(row)) for row in result]
        return result

class NowPlaying(Resource):
    def get(self, theater_id):
        cursor = db.connection.cursor()
        cursor.execute("SELECT m_id, poster_link, title, picked_seat FROM now_play INNER JOIN movie ON now_play.m_id = movie.id WHERE t_id=%s", (theater_id,))
        result = cursor.fetchall()
        result = [dict((cursor.description[i][0], value) \
               for i, value in enumerate(row)) for row in result]
        return result

class UpdateSeat(Resource):
    def post(self, movie_id, theater_id, seat_no):
        conn = db.connection
        cursor = conn.cursor()
        cursor.execute("SELECT picked_seat FROM now_play WHERE t_id=%s AND m_id=%s", (theater_id, movie_id))
        result = cursor.fetchall()
        if result[0][0] != None:
            result = result[0][0] + "," + seat_no
            cursor.execute("UPDATE now_play SET picked_seat = %s WHERE t_id=%s AND m_id=%s", (result, theater_id, movie_id))
        else:
            cursor.execute("UPDATE now_play SET picked_seat = %s WHERE t_id=%s AND m_id=%s", (seat_no, theater_id, movie_id))
        conn.commit()
        
class GetSeat(Resource):
    def get(self, theater_id, movie_id):
        cursor = db.connection.cursor()
        cursor.execute("SELECT picked_seat FROM now_play WHERE t_id=%s AND m_id=%s", (theater_id, movie_id))
        result = cursor.fetchall()
        result = [dict((cursor.description[i][0], value) \
               for i, value in enumerate(row)) for row in result]
        print(result[0])
        return result[0]

api.add_resource(Movie, "/movie/<int:movie_id>")
api.add_resource(Theater, "/theater")
api.add_resource(NowPlaying, "/nowplaying/<int:theater_id>")
api.add_resource(GetSeat, "/getseat/<int:movie_id>&<string:theater_id>")
api.add_resource(UpdateSeat, "/seat/<int:movie_id>&<string:theater_id>&<string:seat_no>")


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True, threaded=True)
